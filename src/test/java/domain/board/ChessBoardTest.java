package domain.board;

import fixture.PieceFixture;
import fixture.PositionFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ChessBoardTest {

    ChessBoard chessBoard;

    @BeforeEach
    void setUp() {
        chessBoard = new ChessBoard(ChessBoardInitializer.init());
        ;
    }

    @DisplayName("source 에서 target 으로 기물을 옮긴다.")
    @Nested
    class MovePiece {

        @BeforeEach
        void setUp() {
            chessBoard.move(PositionFixture.a2(), PositionFixture.a4());
        }

        @DisplayName("움직인 후 기존 위치의 기물은 제거된다.")
        @Test
        void removePieceAfterMove() {
            assertThatThrownBy(() -> chessBoard.findPiece(PositionFixture.a2()))
                    .isInstanceOf(IllegalArgumentException.class).hasMessage("해당 위치에 기물이 없습니다.");
        }

        @DisplayName("목적지에 기물을 놓는다.")
        @Test
        void putPieceAfterMove() {
            assertThat(chessBoard.findPiece(PositionFixture.a4())).isEqualTo(PieceFixture.whitePawn());
        }
    }
}
