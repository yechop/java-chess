package view;

import domain.Command;
import domain.board.File;
import domain.board.Rank;
import domain.position.Position;
import view.mapper.CommandMapper;
import view.mapper.FileMapper;
import view.mapper.RankMapper;

import java.util.Scanner;

public class InputView {

    private static final String LINE_SEPARATOR = System.lineSeparator();

    private final Scanner scanner = new Scanner(System.in);

    public Command readInitCommand() {
        System.out.println(String.join(LINE_SEPARATOR, "> 체스 게임을 시작합니다.",
                "> 게임 시작 : start",
                "> 게임 종료 : end",
                "> 게임 이동 : move source위치 target위치 - 예. move b2 b3",
                "> 점수 현황 : status"));
        return CommandMapper.toInitCommand(scanner.next());
    }

    public Command readPlayCommand() {
        return CommandMapper.toPlayCommand(scanner.next());
    }

    public Position readPosition() {
        if (scanner.hasNext()) {
            String position = scanner.next();
            if (position.length() != 2) {
                throw new IllegalArgumentException("포지션은 file과 rank를 붙여 입력해주세요.");
            }
            File file = FileMapper.from(position.substring(0, 1));
            Rank rank = RankMapper.from(position.substring(1, 2));

            return Position.valueOf(file, rank);
        }
        throw new IllegalArgumentException("move 명령어 이후 source와 target을 입력해주세요.");
    }

    public void readNextLine() {
        scanner.nextLine();
    }
}
