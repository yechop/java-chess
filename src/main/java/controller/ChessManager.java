package controller;

import domain.Command;
import domain.Turn;
import domain.board.ChessBoard;
import domain.board.ChessBoardInitializer;
import domain.dao.ChessGameDaoImpl;
import domain.dao.ChessGameService;
import domain.dao.UserDao;
import domain.position.Position;
import domain.status.GameStatus;
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
        String roomName = inputView.readRoomName();

        ChessBoard chessBoard;
        Turn turn;
        ChessGameDaoImpl chessGameDaoImpl = new ChessGameDaoImpl(new UserDao());
        ChessGameService chessGameService = new ChessGameService(chessGameDaoImpl);
        try {
            chessBoard = new ChessBoard(chessGameService.loadBoard(roomName));
            turn = chessGameService.loadTurn(roomName);
            outputView.printLoadGame(roomName);
        } catch (IllegalArgumentException illegalArgumentException) {
            chessBoard = new ChessBoard(ChessBoardInitializer.init());
            turn = new Turn();
            outputView.printNewGame(roomName);
        }

        while (command.isNotEnd()) {
            processIfStartCommand(command, chessBoard);
            processIfMoveCommand(command, chessBoard, turn);
            processIfStatusCommand(command, chessBoard);

            chessGameService.saveData(roomName, chessBoard, turn);
            command = decideGameCommand(roomName, chessBoard, chessGameService);
        }
    }

    private void processIfStartCommand(Command command, ChessBoard chessBoard) {
        if (command.isStart()) {
            outputView.printChessBoard(chessBoard);
        }
    }

    private void processIfMoveCommand(Command command, ChessBoard chessBoard, Turn turn) {
        if (command.isMove()) {
            progressOneTurn(chessBoard, turn);
        }
    }

    private void processIfStatusCommand(Command command, ChessBoard chessBoard) {
        if (command.isStatus()) {
            GameStatus gameStatus = GameStatus.from(chessBoard);
            outputView.printStatus(gameStatus);
        }
    }

    private Command decideGameCommand(String roomName, ChessBoard chessBoard, ChessGameService chessGameService) {
        if (chessBoard.isKingAlive()) {
            return inputView.readPlayCommand();
        }
        chessGameService.resetBoard(roomName);
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
