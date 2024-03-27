package view;

import domain.board.ChessBoard;
import domain.status.GameStatus;

public class OutputView {

    private final MessageResolver messageResolver;

    public OutputView(MessageResolver messageResolver) {
        this.messageResolver = messageResolver;
    }

    public void printChessBoard(ChessBoard chessBoard) {
        System.out.println(messageResolver.resolveChessBoardMessage(chessBoard));
    }

    public void printStatus(GameStatus gameStatus) {
        System.out.println(messageResolver.resolveStatusMessage(gameStatus));
    }
}
