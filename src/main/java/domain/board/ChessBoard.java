package domain.board;

import domain.piece.Piece;
import domain.position.Position;

import java.util.Map;

public class ChessBoard {

    private final Map<Position, Piece> board;

    public ChessBoard(Map<Position, Piece> board) {
        this.board = board;
    }

    public boolean hasPiece(Position position) {
        return board.containsKey(position);
    }

    public Piece findPiece(Position position) {
        if (isEmpty(position)) {
            throw new IllegalArgumentException("해당 위치에 기물이 없습니다.");
        }
        return board.get(position);
    }

    private boolean isEmpty(Position position) {
        return !board.containsKey(position);
    }

    public void move(Position current, Position target) {
        Piece piece = findMovablePiece(current, target);
        board.remove(current);
        board.put(target, piece);
    }

    private Piece findMovablePiece(Position current, Position target) {
        Map<Position, Piece> piecesOnPath = findPiecesOnPath(current, target);

        Piece piece = board.get(current);
        piece.checkMovable(current, target, piecesOnPath);
        return piece;
    }

    private Map<Position, Piece> findPiecesOnPath(Position current, Position target) {
        Path path = new Path(current, target);
        return path.findPieces(target, board);
    }
}
