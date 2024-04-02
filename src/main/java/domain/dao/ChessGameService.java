package domain.dao;

import domain.Turn;
import domain.board.ChessBoard;
import domain.dao.mapper.BoardDaoMapper;
import domain.dao.mapper.TurnDaoMapper;
import domain.piece.Piece;
import domain.position.Position;

import java.util.Map;

public class ChessGameService {

    private final ChessGameDao chessGameDao;

    public ChessGameService(ChessGameDao chessGameDao) {
        this.chessGameDao = chessGameDao;
    }

    public void saveData(ChessBoard chessBoard, Turn turn) {
        String boardData = BoardDaoMapper.boardToData(chessBoard.getBoard());
        String turnData = TurnDaoMapper.turnToData(turn);

        chessGameDao.saveData(boardData, turnData);
    }

    public void resetBoard() {
        chessGameDao.resetChessBoard();
    }

    public Map<Position, Piece> loadBoard() {
        String data = chessGameDao.dataToBoard();
        return BoardDaoMapper.dataToBoard(data);
    }

    public Turn loadTurn() {
        String data = chessGameDao.dataToTurn();
        return TurnDaoMapper.dataToTurn(data);
    }
}
