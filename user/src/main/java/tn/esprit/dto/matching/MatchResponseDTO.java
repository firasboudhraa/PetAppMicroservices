package tn.esprit.dto.matching;

import lombok.Data;

import java.util.List;

@Data
public class MatchResponseDTO {
    private List<Match> matches;

    @Data
    public static class Match {
        private String id;
        private int match_score;
        private List<String> reasons;
        private String consideration;
    }
}