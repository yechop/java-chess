package domain;

import domain.board.ChessBoard;
import domain.piece.Piece;
import domain.position.Position;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ScoreCalculator {

    ChessBoard chessBoard;

    public ScoreCalculator(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
    }

    public double sumMajorPieceScore(Side side) {
        List<Piece> majorPieces = chessBoard.collectMajorPieces(side);
        return majorPieces.stream()
                .mapToDouble(Piece::getScore)
                .sum();
    }

    public double sumPawnScore(Side side) {
        Map<Position, Piece> pawns = chessBoard.collectPawns(side);
        return pawns.entrySet().stream()
                .mapToDouble(entry -> pawnScore(entry.getKey(), entry.getValue(), pawns.keySet()))
                .sum();
    }

    private double pawnScore(Position pawnPosition, Piece pawn, Set<Position> positions) {
        return positions.stream()
                .filter(position -> !pawnPosition.equals(position))
                .filter(pawnPosition::isSameFile)
                .findFirst()
                .map(score -> pawn.decreaseScore())
                .orElse(pawn.getScore());
    }
}
