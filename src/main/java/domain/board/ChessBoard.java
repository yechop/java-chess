package domain.board;

import domain.Side;
import domain.piece.*;
import domain.position.InitPosition;
import domain.position.Position;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ChessBoard {

    private final Map<Position, Piece> board;

    public ChessBoard(Map<Position, Piece> board) {
        this.board = board;
        initChessBoard();
    }

    public ChessBoard() {
        this(new LinkedHashMap<>());
    }

    private void initChessBoard() {
        Arrays.stream(Side.values()).forEach(this::initSide);
    }

    private void initSide(Side side) {
        initPiece(InitPosition.ROOK, side, () -> new Rook(side));
        initPiece(InitPosition.KNIGHT, side, () -> new Knight(side));
        initPiece(InitPosition.BISHOP, side, () -> new Bishop(side));
        initPiece(InitPosition.QUEEN, side, () -> new Queen(side));
        initPiece(InitPosition.KING, side, () -> new King(side));
        initPiece(InitPosition.PAWN, side, () -> new Pawn(side));
    }

    private void initPiece(InitPosition initPosition, Side side, Supplier<Piece> pieceSupplier) {
        List<File> files = initPosition.files();
        Rank rank = initPosition.rank(side);
        files.forEach(file -> board.put(Position.valueOf(file, rank), pieceSupplier.get()));
    }

    public boolean hasPiece(Position position) {
        return board.containsKey(position);
    }

    public Piece findPiece(Position position) {
        if (!hasPiece(position)) {
            throw new IllegalArgumentException("해당 위치에 기물이 없습니다.");
        }
        return board.get(position);
    }

    public void move(Position current, Position target) {
        validateNotSamePosition(current, target);

        Piece piece = findMovablePiece(current, target);
        board.remove(current);
        board.put(target, piece);
    }

    private void validateNotSamePosition(Position current, Position target) {
        if (current.equals(target)) {
            throw new IllegalArgumentException("source 위치와 target 위치가 같습니다.");
        }
    }

    private Piece findMovablePiece(Position current, Position target) {
        Map<Position, Piece> piecesOnPath = findPiecesOnPath(current, target);

        Piece piece = board.get(current);
        boolean canPieceMove = piece.canMove(current, target, piecesOnPath);
        if (!canPieceMove) {
            throw new IllegalArgumentException("이동할 수 없는 target 입니다.");
        }
        return piece;
    }

    private Map<Position, Piece> findPiecesOnPath(Position current, Position target) {
        Path path = new Path(current, target);
        return path.findPieces(board);
    }

    public Map<Position, Piece> getBoard() {
        return board;
    }
}
