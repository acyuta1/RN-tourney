package com.acyuta.rf.tournament.core.service;

import com.acyuta.rf.rafantasyShared.dto.external.PageRequestDto;
import com.acyuta.rf.rafantasyShared.dto.tourney.TournamentDto;
import com.acyuta.rf.rafantasyShared.feign.external.TennisClient;
import com.acyuta.rf.tournament.core.mappers.TournamentMapper;
import com.acyuta.rf.tournament.core.model.AtpType;
import com.acyuta.rf.tournament.core.model.Tournament;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import static com.acyuta.rf.tournament.core.util.Constants.ATP_TYPES;
import static com.acyuta.rf.tournament.core.util.Constants.GRAND_SLAMS;

@Service
@RequiredArgsConstructor
@Slf4j
public class TournamentService {

    private final TournamentRepository tournamentRepository;

    private final TournamentMapper tournamentMapper;

    private final TennisClient tennisClient;

    public Page<TournamentDto> fetchAll(int page, int size) {
        return tournamentRepository.findAll(PageRequest.of(page, size)).map(tournamentMapper::toDto);
    }

    public Tournament findById(Long tournamentId) {
        return tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("tournament with ID - %s not found", tournamentId)));
    }

    public TournamentDto save(Tournament tournament) {
        return tournamentMapper.toDto(tournamentRepository.save(tournament));
    }

    @Transactional
    public TournamentDto addTournament(TournamentDto tournamentDto, MultipartFile image, String path) {
        // Normalize file name

        var tournament = tournamentMapper.fromDto(tournamentDto);
        tournamentDto = save(tournament);
        var fileName = storeTournamentImage(image, path, tournamentDto);
        tournamentDto.setImageUniqueName(fileName);
        return save(tournamentMapper.fromDto(tournamentDto));
    }

    private String storeTournamentImage(MultipartFile image, String path, TournamentDto tournamentDto) {
        String fileName = StringUtils.cleanPath(image.getOriginalFilename());
        fileName += String.format("%s_%s", tournamentDto.getId(), fileName);
        try {
            var absolutePath = Paths.get(path)
                    .toAbsolutePath().normalize();

            Files.createDirectories(absolutePath);
            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = absolutePath.resolve(fileName);
            Files.copy(image.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            log.error("Error occurred while adding new Tournament - {}", ex.getMessage());
            ex.getMessage();
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("could not add image - %s", path));
    }

    public void initTournaments() {
        tournamentRepository.deleteAll();
        try {
            var response = tennisClient.getTournaments("tennis");
            JSONObject tournamentInfoResponse = new JSONObject(response);

            // CCG field contains list of tournaments.
            JSONArray tournaments = new JSONArray(tournamentInfoResponse.getJSONArray("Ccg").toString());
            var tournamentList = new ArrayList<TournamentDto>();

            // iterate over the Tournaments.
            for (int i = 0; i < tournaments.length(); i++) {
                JSONObject tournamentJson = tournaments.getJSONObject(i);

                // GrandSlams are not nested, but ATP1000/500/250 are nested inside "Stages".
                String tournamentName = tournamentJson.getString("Cnm");
                if (GRAND_SLAMS.contains(tournamentName)) {
                    tournamentList.add(constructTournament(
                            tournamentName,
                            AtpType.GRAND_SLAM.toString(),
                            tournamentJson.getString("Ccd")));
                } else if (ATP_TYPES.contains(tournamentName)) {
                    JSONArray subTournaments = new JSONArray(tournamentJson.getJSONArray("Stages").toString());
                    for (int j = 0; j < subTournaments.length(); j++) {

                        JSONObject subTournament = subTournaments.getJSONObject(j);
                        String subTournamentName = subTournament.getString("Sdn");

                        // For now, exclude Qualification.
                        if (!subTournamentName.toLowerCase(Locale.ROOT).contains("qualification")) {
                            tournamentList.add(constructTournament(
                                    subTournamentName,
                                    String.format("%s_%s", tournamentJson.getString("Ccd"), subTournament.getString("Scd")),
                                    getAtpType(tournamentName)));
                        }
                    }
                }
            }
            HashSet<Object> seen = new HashSet<>();
            tournamentList.removeIf(e -> !seen.add(e.getTournamentName()));
            tournamentRepository.saveAll(tournamentMapper.fromDtoList(tournamentList));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String getAtpType(String cnm) {
        cnm = cnm.replaceAll(" ", "");
        if (cnm.toLowerCase(Locale.ROOT).contains("atp")) {
            if (cnm.contains("250"))
                return AtpType.ATP250.toString();
            if (cnm.contains("500"))
                return AtpType.ATP500.toString();
            if (cnm.contains("1000"))
                return AtpType.ATP1000.toString();
            return AtpType.ATP250.toString();
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "bad type of tournament Sdn");
    }

    private TournamentDto constructTournament(String name, String atpType, String uniqueName) {
        return new TournamentDto()
                .setTournamentName(name)
                .setAtpType(atpType)
                .setUniqueIdentityName(uniqueName);
    }
}
