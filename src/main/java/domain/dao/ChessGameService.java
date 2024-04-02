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

    public void saveData(String roomName, ChessBoard chessBoard, Turn turn) {
        chessGameDao.deleteBoard(roomName);
        String boardData = BoardDaoMapper.boardToData(chessBoard.getBoard());
        String turnData = TurnDaoMapper.turnToData(turn);
        chessGameDao.saveData(roomName, boardData, turnData);
    }

    public Map<Position, Piece> loadBoard(String roomName) {
        String data = chessGameDao.dataToBoard(roomName);
        return BoardDaoMapper.dataToBoard(data);
    }

    public Turn loadTurn(String roomName) {
        String data = chessGameDao.dataToTurn(roomName);
        return TurnDaoMapper.dataToTurn(data);
    }

    public void resetBoard(String roomName) {
        chessGameDao.deleteBoard(roomName);
    }
}
