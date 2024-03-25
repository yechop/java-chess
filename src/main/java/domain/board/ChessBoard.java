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

    public Map<Position, Piece> getBoard() {
        return board;
    }
}
