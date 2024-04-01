package domain.dao;

import domain.Turn;
import domain.board.File;
import domain.board.Rank;
import domain.piece.Piece;
import domain.position.Position;
import view.mapper.FileMapper;
import view.mapper.PieceMapper;
import view.mapper.RankMapper;
import view.mapper.SideMapper;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ChessDaoMapper {

    public static String boardToData(Map<Position, Piece> board) {
        return board.entrySet().stream()
                .map(entry -> entry.getKey().toData() + entry.getValue().toData())
                .collect(Collectors.joining(","));
    }

    public static Map<Position, Piece> dataToBoard(String gameStatus) {
        Map<Position, Piece> board = new LinkedHashMap<>();

        List<String> dataList = List.of(gameStatus.split(","));
        for (String data : dataList) {
            File file = FileMapper.from(data.substring(0, 1));
            Rank rank = RankMapper.from(data.substring(1, 2));
            Position position = Position.valueOf(file, rank);

            Piece piece = PieceMapper.toPiece(data.substring(2, 3));

            board.put(position, piece);
        }
        return board;
    }

    public static Turn dataToTurn(String turn) {
        return new Turn(SideMapper.toSide(turn));
    }
}
