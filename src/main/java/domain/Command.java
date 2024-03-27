package domain;

public enum Command {

    START,
    END,
    MOVE,
    STATUS,
    ;

    public boolean isEnd() {
        return this == END;
    }

    public boolean isMove() {
        return this == MOVE;
    }

    public boolean isStart() {
        return this == START;
    }

    public boolean isStatus() {
        return this == STATUS;
    }
}
