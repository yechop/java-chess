package domain;

import domain.board.ChessBoard;

public class GameStatus {

    private double blackScore;
    private double whiteScore;

    public GameStatus() {
        this.blackScore = 0;
        this.whiteScore = 0;
    }

    public void calculateStatusScore(ChessBoard chessBoard) {
        ScoreCalculator scoreCalculator = new ScoreCalculator(chessBoard);

        blackScore = scoreCalculator.sumPiecesScore(Side.BLACK);
        whiteScore = scoreCalculator.sumPiecesScore(Side.WHITE);
    }

    public double blackScore() {
        return blackScore;
    }

    public double whiteScore() {
        return whiteScore;
    }

    public Side winner() {
        if (blackScore > whiteScore) {
            return Side.BLACK;
        }
        if (blackScore == whiteScore) {
            return Side.NONE;
        }
        return Side.WHITE;
    }
}
