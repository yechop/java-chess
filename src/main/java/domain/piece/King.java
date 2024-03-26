package domain.piece;

import domain.Side;
import domain.position.Position;

import java.util.Map;

public class King extends Piece {

    public King(Side side) {
        super(side);
    }

    @Override
    public boolean isKing() {
        return true;
    }

    @Override
    public boolean canMove(Position current, Position target, Map<Position, Piece> pieces) {
        return hasOnlyOneFileGap(current, target) ||
                hasOnlyOneRankGap(current, target) ||
                hasOnlyOneDiagonalGap(current, target);
    }

    private boolean hasOnlyOneFileGap(Position current, Position target) {
        return current.hasOneFileGap(target) &&
                current.isSameRank(target);
    }

    private boolean hasOnlyOneRankGap(Position current, Position target) {
        return current.hasOneRankGap(target) &&
                current.isSameFile(target);
    }

    private boolean hasOnlyOneDiagonalGap(Position current, Position target) {
        return current.isDiagonal(target) &&
                current.hasOneFileGap(target);
    }
}
