package domain.piece;

import domain.Side;
import domain.position.Position;

import java.util.Map;

public class Bishop extends Piece {

    public Bishop(Side side) {
        super(side);
    }

    @Override
    public boolean isBishop() {
        return true;
    }

    @Override
    public boolean canMove(Position current, Position target, Map<Position, Piece> pieces) {
        return current.isDiagonal(target);
    }
}
