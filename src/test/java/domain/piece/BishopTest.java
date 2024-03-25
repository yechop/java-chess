package domain.piece;

import domain.position.Position;
import fixture.PieceFixture;
import fixture.PositionFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.LinkedHashMap;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class BishopTest {

    private static Stream<Arguments> movableTargetPosition() {
        return Stream.of(
                Arguments.arguments(PositionFixture.c5()),
                Arguments.arguments(PositionFixture.e5()),
                Arguments.arguments(PositionFixture.b6()),
                Arguments.arguments(PositionFixture.f6())
        );
    }

    private static Stream<Arguments> immovableTargetPosition() {
        return Stream.of(
                Arguments.arguments(PositionFixture.c4()),
                Arguments.arguments(PositionFixture.d5())
        );
    }

    @DisplayName("비숍은 대각선 방향으로 임의의 칸 수만큼 움직인다.")
    @ParameterizedTest
    @MethodSource("movableTargetPosition")
    void canMoveTest(Position target) {
        Bishop bishop = PieceFixture.blackBishop();

        Position current = PositionFixture.d4();

        boolean actual = bishop.canMove(current, target, new LinkedHashMap<>());

        assertThat(actual).isTrue();
    }

    @DisplayName("비숍은 수직 또는 수평 방향으로 움직일 수 없다.")
    @ParameterizedTest
    @MethodSource("immovableTargetPosition")
    void cantMoveTest(Position target) {
        Bishop bishop = PieceFixture.blackBishop();

        Position current = PositionFixture.d4();

        boolean actual = bishop.canMove(current, target, new LinkedHashMap<>());

        assertThat(actual).isFalse();
    }
}