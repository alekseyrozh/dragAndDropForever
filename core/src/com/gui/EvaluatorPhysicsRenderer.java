package com.gui;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.roomfurniture.box2d.PhysicsSimulatorEvaluator;

import java.awt.*;
import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EvaluatorPhysicsRenderer extends ApplicationAdapter implements InputProcessor {


    static final int WIDTH = 100;
    static final int HEIGHT = 100;


    private SpriteBatch batch;

    private BitmapFont font;

    private MyShapeRenderer shapeRenderer;
    private OrthographicCamera cam;

    private PhysicsSimulatorEvaluator physicsSimulator;

    private int renderType = 0;

    private Box2DDebugRenderer box2DDebugRenderer;
    //private PhysicsSimulator physicsSimulator;


    @Override
    public void create() {
        Gdx.input.setInputProcessor(this);
        // constructObjects();

        box2DDebugRenderer = new Box2DDebugRenderer();

        font = new BitmapFont();
        font.setColor(Color.BLACK);
        batch = new SpriteBatch();

        shapeRenderer = new MyShapeRenderer();

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();


        cam = new OrthographicCamera(WIDTH, HEIGHT * (h / w));

        cam.zoom = 1f;
        cam.update();
    }


//    private void constructObjects() {
//        //solution
//
//        this.itemsInRoom = solution.getItemsInTheRoom(problem);
//
//
//        //spread
//        List<Furniture> notIncludedItems = new ArrayList<>();
//
//
//        Comparator<Furniture> comparator = Comparator.comparing(item -> item.getScorePerUnitArea() * ShapeCalculator.calculateAreaOf(item.toShape()));
//        notIncludedItems.sort(comparator);
//
//        notIncludedItems.addAll(problem.getFurnitures());
//
//
//        for (Furniture furniture : itemsInRoom) {
//            int ind = notIncludedItems.indexOf(furniture);
//            if (ind != -1)
//                notIncludedItems.remove(ind);
//        }
//
//        this.notIncludedItems = spread(notIncludedItems);
//    }


    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        handleInput();
        cam.update();
        shapeRenderer.setProjectionMatrix(cam.combined);

        if (physicsSimulator != null) {
            box2DDebugRenderer.render(physicsSimulator.world, cam.combined);
        }

        renderRepelPoint();

        //
//
//        physicsSimulator.update(Gdx.graphics.getDeltaTime());
//
//        if (renderType != 3)
//            Gdx.gl.glClearColor(1, 1, 1, 1);
//        else
//            Gdx.gl.glClearColor(0, 0, 0, 1);
//
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//
//        handleInput();
//        cam.update();
//
//        shapeRenderer.setProjectionMatrix(cam.combined);
//
//        //colours
//        double maxValue = 0;
//        for (Furniture item : problem.getFurnitures()) {
//            maxValue = Math.max(maxValue, item.getScorePerUnitArea());
//        }
//
//
//        switch (renderType) {
//            case 0:
//                renderRoom();
//                renderInItems(maxValue);
//                renderOutItems(maxValue);
//                break;
//            case 1:
//                renderRoom();
//                renderOutItems(maxValue);
//                break;
//            case 2:
//                renderRoom();
//                renderInItems(maxValue);
//                break;
//            case 3:
//                box2DDebugRenderer.render(physicsSimulator.world, cam.combined);
//                break;
//        }
//
//        renderRepelPoint();
//
//
//        batch.begin();
//
//        int y = 50;
//        font.draw(batch, "zoomSpeed: " + zoomInc, 10, y += 20);
//        font.draw(batch, "translateSpeed: " + translateInc, 10, y += 40);
//
//        font.draw(batch, "Score is " + solution.score(problem).get(), 10, y += 20);
//        font.draw(batch, "Coverage: " + solution.findCoverage(problem) * 100 + "%", 10, y += 20);
//
//        batch.end();
    }

    private void renderRepelPoint() {
        if (physicsSimulator.getRepelPoint() == null)
            return;

        Vector2 repelPoint = physicsSimulator.getRepelPoint();
        shapeRenderer.begin(MyShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLUE);

        shapeRenderer.line(repelPoint.x - cam.viewportWidth * cam.zoom, repelPoint.y,
                repelPoint.x + cam.viewportWidth * cam.zoom, repelPoint.y);

        shapeRenderer.line(repelPoint.x, repelPoint.y - cam.viewportHeight * cam.zoom,
                repelPoint.x, repelPoint.y + cam.viewportHeight * cam.zoom);


        shapeRenderer.end();
    }


    private float zoomInc = 1.05f;
    private float zoomUnit = 0.1f;

    private float translateInc = 10f;
    private float translateUnit = 1f;


    private float rotateInc = 0.5f;
    private float rotateUnit = 1f;


    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            cam.zoom += zoomInc;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            cam.zoom -= zoomInc;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            cam.translate(-translateInc, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            cam.translate(translateInc, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            cam.translate(0, -translateInc, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            cam.translate(0, translateInc, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            cam.rotate(-rotateInc, 0, 0, 1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            cam.rotate(rotateInc, 0, 0, 1);
        }
        cam.zoom = Math.max(cam.zoom, 0.1f);
        //cam.zoom = MathUtils.clamp(cam.zoom, 0.1f, 100 / cam.viewportWidth);

//        float effectiveViewportWidth = cam.viewportWidth * cam.zoom;
//        float effectiveViewportHeight = cam.viewportHeight * cam.zoom;
//
//        cam.position.x = MathUtils.clamp(cam.position.x, effectiveViewportWidth / 2f, 100 - effectiveViewportWidth / 2f);
//        cam.position.y = MathUtils.clamp(cam.position.y, effectiveViewportHeight / 2f, 100 - effectiveViewportHeight / 2f);
    }

//    public void resize(int width, int height) {
//        cam.viewportWidth = width;
//        cam.viewportHeight = HEIGHT * (width / height);
//
//        cam.update();
//    }


    @Override
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
        //img.dispose();
    }


    public static float[] getPoints(Shape path) {
        List<double[]> pointList = new ArrayList<double[]>();
        double[] coords = new double[6];
        int numSubPaths = 0;
        for (PathIterator pi = path.getPathIterator(null);
             !pi.isDone();
             pi.next()) {
            switch (pi.currentSegment(coords)) {
                case PathIterator.SEG_MOVETO:
                    pointList.add(Arrays.copyOf(coords, 2));
                    ++numSubPaths;
                    break;
                case PathIterator.SEG_LINETO:
                    pointList.add(Arrays.copyOf(coords, 2));
                    break;
                case PathIterator.SEG_CLOSE:
                    if (numSubPaths > 1) {
                        throw new IllegalArgumentException("Path contains multiple subpaths");
                    }
                    double[][] points = pointList.toArray(new double[pointList.size()][]);


                    float[] finalReturn = new float[points.length * 2];

                    for (int i = 0; i < pointList.size(); i++) {
                        finalReturn[i * 2] = (float) points[i][0];
                        finalReturn[i * 2 + 1] = (float) points[i][1];
                    }
                    return finalReturn;

                default:
                    throw new IllegalArgumentException("Path contains curves");
            }
        }
        throw new IllegalArgumentException("Unclosed path");
    }


    //0..1
    public static Color valueToColor(float alpha) {
        return new Color(Color.rgba8888(0, 1 - alpha, 0, 1));
    }

    //note, hue is on 0-360 scale.
    public static Color hueToSaturatedColor(float hue) {
        hue *= 360;
        float r, g, b;
        float Hprime = hue / 60;
        float X = 1 - Math.abs(Hprime % 2 - 1);
        if (Hprime < 1) {
            r = 1;
            g = X;
            b = 0;
        } else if (Hprime < 2) {
            r = X;
            g = 1;
            b = 0;
        } else if (Hprime < 3) {
            r = 0;
            g = 1;
            b = X;
        } else if (Hprime < 4) {
            r = 0;
            g = X;
            b = 1;
        } else if (Hprime < 5) {
            r = X;
            g = 0;
            b = 1;
        } else if (Hprime < 6) {
            r = 1;
            g = 0;
            b = X;
        } else {
            r = 0;
            g = 0;
            b = 0;
        }

        return new Color(r, g, b, 1);
    }

    @Override
    public boolean keyDown(int keycode) {

        if (Gdx.input.isKeyPressed(Input.Keys.G)) {
            renderType = (renderType + 1) % 4;
        }


        if (Gdx.input.isKeyPressed(Input.Keys.EQUALS)) {
            translateInc += translateUnit;
            zoomInc += zoomUnit;
            rotateInc += rotateUnit;

        }

        if (Gdx.input.isKeyPressed(Input.Keys.MINUS)) {
            translateInc -= translateUnit;
            zoomInc -= zoomUnit;
            rotateInc -= rotateUnit;

        }

        return true;
    }

    Vector2 getMousePosInWorld() {
        Vector3 unprojected = cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        return new Vector2(unprojected.x, unprojected.y);
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        return false;
    }


    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public void update(PhysicsSimulatorEvaluator physicsSimulator) {
        this.physicsSimulator = physicsSimulator;
    }
}
