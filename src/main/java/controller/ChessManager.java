package controller;

import domain.Command;
import domain.Turn;
import domain.board.ChessBoard;
import domain.board.ChessBoardInitializer;
import domain.position.Position;
import view.InputView;
import view.OutputView;

public class ChessManager {

    private final InputView inputView;
    private final OutputView outputView;

    public ChessManager(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void start() {
        Command command = inputView.readInitCommand();
        inputView.readNextLine();
        if (command.isEnd()) {
            return;
        }

        //ChessBoard chessBoard = new ChessBoard();
        ChessBoard chessBoard = new ChessBoard(ChessBoardInitializer.init());
        outputView.printChessBoard(chessBoard);

        startChessGame(chessBoard);
    }

    private void startChessGame(ChessBoard chessBoard) {
        Command command = inputView.readPlayCommand();
        Turn turn = new Turn();

        while (command.isMove()) {
            progressOneTurn(chessBoard, turn);
            command = inputView.readPlayCommand();
        }
    }

    private void progressOneTurn(ChessBoard chessBoard, Turn turn) {
        Position current = inputView.readPosition();
        Position target = inputView.readPosition();
        inputView.readNextLine();

        turn.check(chessBoard, current);
        chessBoard.move(current, target);
        turn.end();

        outputView.printChessBoard(chessBoard);
    }
}
