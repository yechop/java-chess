package domain.dao;

public interface ChessGameDao {

    void saveData(String boardData, String turnData);

    void resetChessBoard();

    String dataToBoard();

    String dataToTurn();
}
