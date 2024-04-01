package domain.dao;

import domain.Side;
import domain.Turn;
import domain.piece.Piece;
import domain.position.Position;
import fixture.PieceFixture;
import fixture.PositionFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ChessDaoMapperTest {

    @DisplayName("보드를 db용 데이터로 만든다.")
    @Test
    void boardToData() {
        Map<Position, Piece> board = new LinkedHashMap<>();
        board.put(PositionFixture.a1(), PieceFixture.whiteRook());
        board.put(PositionFixture.a2(), PieceFixture.whitePawn());

        String data = ChessDaoMapper.boardToData(board);
        assertThat(data).isEqualTo("a1r,a2p");
    }

    @DisplayName("board 데이터를 보드로 만든다.")
    @Test
    void dataToBoard() {
        String data = "a1r,a2p";
        Map<Position, Piece> board = ChessDaoMapper.dataToBoard(data);

        assertThat(board).containsExactly(
                Map.entry(PositionFixture.a1(), PieceFixture.whiteRook()),
                Map.entry(PositionFixture.a2(), PieceFixture.whitePawn())
        );
    }

    @DisplayName("turn 데이터를 턴으로 만든다")
    @Test
    void dataToTurn() {
        String data = "WHITE";
        Turn turn = ChessDaoMapper.dataToTurn(data);

        assertThat(turn).isEqualTo(new Turn(Side.WHITE));
    }
}
