package domain.board;

import domain.piece.Piece;
import domain.position.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Path {

    private final List<Position> path;

    public Path(Position current, Position target) {
        path = makePath(current, target);
    }

    private List<Position> makePath(Position current, Position target) {
        if (current.isDiagonal(target)) {
            return makeDiagonalPath(current, target);
        }
        if (current.isSameRank(target)) {
            return makeHorizontalPath(current, target);
        }
        if (current.isSameFile(target)) {
            return makeVerticalPath(current, target);
        }
        return new ArrayList<>();
    }

    private List<Position> makeDiagonalPath(Position current, Position target) {
        List<File> files = current.findBetweenFile(target);
        List<Rank> ranks = current.findBetweenRank(target);

        return IntStream.range(0, files.size())
                .mapToObj(i -> Position.valueOf(files.get(i), ranks.get(i)))
                .toList();
    }

    private List<Position> makeHorizontalPath(Position current, Position target) {
        List<File> files = current.findBetweenFile(target);

        return files.stream()
                .map(current::createWithSameRank)
                .toList();
    }

    private List<Position> makeVerticalPath(Position current, Position target) {
        List<Rank> files = current.findBetweenRank(target);

        return files.stream()
                .map(current::createWithSameFile)
                .toList();
    }

    public Map<Position, Piece> findPieces(Position target, Map<Position, Piece> board) {
        Map<Position, Piece> pieces = board.entrySet().stream()
                .filter(entry -> path.contains(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        if (board.containsKey(target)) {
            pieces.put(target, board.get(target));
        }

        return pieces;
    }
}
