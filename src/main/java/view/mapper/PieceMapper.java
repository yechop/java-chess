package view.mapper;

import domain.Side;
import domain.piece.*;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public enum PieceMapper {

    ROOK(Rook.class, "R"),
    KNIGHT(Knight.class, "N"),
    BISHOP(Bishop.class, "B"),
    QUEEN(Queen.class, "Q"),
    KING(King.class, "K"),
    PAWN(Pawn.class, "P"),
    ;

    private final Class<? extends Piece> pieceType;
    private final String symbol;

    PieceMapper(Class<? extends Piece> pieceType, String symbol) {
        this.pieceType = pieceType;
        this.symbol = symbol;
    }

    public static String toSymbol(Piece piece) {
        String symbol = Arrays.stream(values())
                .filter(value -> value.pieceType == piece.getClass())
                .findFirst()
                .map(value -> value.symbol)
                .orElseThrow();

        if (piece.isBlack()) {
            return symbol;
        }
        return symbol.toLowerCase();
    }

    public static Piece toPiece(String input) {
        PieceMapper pieceMapper = Arrays.stream(values())
                .filter(value -> value.symbol.equalsIgnoreCase(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당하는 symbol이 없습니다."));

        Side side = deciedSide(input);

        try {
            return pieceMapper.pieceType.getDeclaredConstructor(Side.class).newInstance(side);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Side deciedSide(String input) {
        if (Character.isUpperCase(input.charAt(0))) {
            return Side.BLACK;
        }
        return Side.WHITE;
    }
}
