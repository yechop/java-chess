package controller;

import domain.Command;
import domain.GameStatus;
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
        ChessBoard chessBoard = new ChessBoard(ChessBoardInitializer.init());
        Turn turn = new Turn();
        Command command = inputView.readInitCommand();

        while (!command.isEnd()) {
            runStartCommand(command, chessBoard);
            runMoveCommand(command, chessBoard, turn);
            runStatusCommand(command, chessBoard);

            command = decideGameCommand(chessBoard);
        }
    }

    private void runStartCommand(Command command, ChessBoard chessBoard) {
        if (command.isStart()) {
            outputView.printChessBoard(chessBoard);
        }
    }

    private void runMoveCommand(Command command, ChessBoard chessBoard, Turn turn) {
        if (command.isMove()) {
            progressOneTurn(chessBoard, turn);
        }
    }

    private void runStatusCommand(Command command, ChessBoard chessBoard) {
        if (command.isStatus()) {
            GameStatus gameStatus = new GameStatus();
            gameStatus.calculateStatusScore(chessBoard);
            outputView.printStatus(gameStatus);
        }
    }

    private Command decideGameCommand(ChessBoard chessBoard) {
        if (chessBoard.isKingAlive()) {
            return inputView.readPlayCommand();
        }
        return Command.END;
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
