package com.acyuta.rf.tournament.core.service;

import com.acyuta.rf.rafantasyShared.dto.tourney.PlayerDto;
import com.acyuta.rf.rafantasyShared.dto.tourney.TournamentDto;
import com.acyuta.rf.rafantasyShared.feign.external.TennisLiveDataClient;
import com.acyuta.rf.tournament.core.mappers.PlayerMapper;
import com.acyuta.rf.tournament.core.model.AtpType;
import com.acyuta.rf.tournament.core.model.Player;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import static com.acyuta.rf.tournament.core.util.Constants.ATP_TYPES;
import static com.acyuta.rf.tournament.core.util.Constants.GRAND_SLAMS;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;

    private final PlayerMapper playerMapper;

    private final TennisLiveDataClient tennisLiveDataClient;

    public PlayerDto findPlayer(Long id) {
        return playerMapper.toDto(playerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "player.not.found")));
    }

    public PlayerDto save(Player player) {
        return playerMapper.toDto(playerRepository.save(player));
    }

    @Transactional
    public void initPlayers() {
        playerRepository.deleteAll();

        try {
            var response = tennisLiveDataClient.getPlayers();
            JSONArray players =
                    new JSONArray(new JSONObject(response).getJSONObject("results").getJSONArray("rankings").toString());

            var playerList = new ArrayList<Player>();
            for (int i=0; i < players.length(); i++) {
                JSONObject playerJson = players.getJSONObject(i);
                playerList.add(playerMapper.fromDto(getConstructedPlayer(playerJson)));
                if (i % 50 == 0) {
                    playerRepository.saveAll(playerList);
                    playerList.clear();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private PlayerDto getConstructedPlayer(JSONObject playerJson) throws JSONException {
        return new PlayerDto()
                .setClientId(playerJson.getLong("id"))
                .setCountry(playerJson.getString("country"))
                .setFullName(playerJson.getString("full_name"))
                .setRank(playerJson.getInt("ranking"));
    }

    public List<PlayerDto> getPlayers() {
        return playerMapper.toDtoList(playerRepository.findAll());
    }
}
