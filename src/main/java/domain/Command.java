package domain;

public enum Command {

    START,
    END,
    MOVE,
    ;

    public boolean isEnd() {
        return this == END;
    }

    public boolean isMove() {
        return this == MOVE;
    }

    public boolean isNotMove() {
        return this != MOVE;
    }
}
