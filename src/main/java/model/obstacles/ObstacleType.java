package main.java.model.obstacles;

import main.java.ResPath;

/**
 * @author WeiYi Yu
 * @date 2020-05-26
 */
public enum ObstacleType {
    ROCK(ResPath.ROCKS),
    TREE(ResPath.TREE),
    FOREST(ResPath.FOREST);

    private String imgPath;

    ObstacleType(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getImgPath() {
        return imgPath;
    }
}
