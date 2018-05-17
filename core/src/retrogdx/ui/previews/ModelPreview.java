package retrogdx.ui.previews;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.model.MeshPart;
import com.badlogic.gdx.graphics.g3d.utils.FirstPersonCameraController;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.kotcrab.vis.ui.widget.VisImage;

public class ModelPreview extends VisImage implements Disposable, InputProcessor {
    private FrameBuffer frameBuffer;
    private ModelBatch modelBatch;
    private Environment environment;
    private DirectionalLight directionalLight;
    private PerspectiveCamera camera;
    private float cameraSpeed;
    private Model model;
    private ModelInstance modelInstance;

    public ModelPreview(Model model) {
        this.modelBatch = new ModelBatch();
        this.model = model;
        this.modelInstance = new ModelInstance(model);

        this.environment = new Environment();
        this.environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        this.directionalLight = new DirectionalLight();
        this.directionalLight.set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f);
        this.environment.add(this.directionalLight);

        float distance = 0;

        for (MeshPart meshPart : this.model.meshParts) {
            distance = Math.max(distance, meshPart.radius);
        }

        this.camera = new PerspectiveCamera(67, 1, 1);
        this.camera.position.set(0, 0, distance);
        this.camera.near = distance / 1000;
        this.camera.far = distance * 10;
        this.camera.update();

        this.cameraSpeed = distance;

        ModelPreview self = this;

        this.addCaptureListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.input.setInputProcessor(self);
                Gdx.input.setCursorCatched(true);

                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }

    protected void sizeChanged() {
        super.sizeChanged();

        this.camera.viewportWidth = this.getWidth();
        this.camera.viewportHeight = this.getHeight();
        this.camera.update();

        if (this.frameBuffer != null) {
            this.frameBuffer.dispose();
        }

        this.frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, (int) this.getWidth(), (int) this.getHeight(), true);
    }

    public void draw(Batch batch, float parentAlpha) {
        this.directionalLight.direction.set(camera.direction);

        batch.end();
        this.frameBuffer.begin();

        Gdx.gl.glViewport(0, 0, this.frameBuffer.getWidth(), this.frameBuffer.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        this.modelBatch.begin(this.camera);
        this.modelBatch.render(this.modelInstance, this.environment);
        this.modelBatch.end();

        this.frameBuffer.end();
        batch.begin();

        TextureRegion region = new TextureRegion(this.frameBuffer.getColorBufferTexture());
        region.flip(false, true);
        this.setDrawable(new TextureRegionDrawable(region));

        super.draw(batch, parentAlpha);
    }

    public void dispose() {
        this.model.dispose();
        this.modelBatch.dispose();
        this.frameBuffer.dispose();
    }

    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.ESCAPE) {
            Gdx.input.setCursorCatched(false);
            Gdx.input.setInputProcessor(this.getStage());
        }

        return false;
    }

    public boolean keyUp(int keycode) {
        return false;
    }

    public boolean keyTyped(char character) {
        return false;
    }

    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    public boolean mouseMoved(int screenX, int screenY) {
        float deltaX = -Gdx.input.getDeltaX() * 0.1f;
        float deltaY = -Gdx.input.getDeltaY() * 0.1f;
        camera.direction.rotate(camera.up, deltaX);
        Vector3 tmp = new Vector3();
        tmp.set(camera.direction).crs(camera.up).nor();
        camera.direction.rotate(tmp, deltaY);

        return false;
    }

    public boolean scrolled(int amount) {
        if (amount < 0) {
            cameraSpeed *= 1.1;
        } else if (amount > 0) {
            cameraSpeed /= 1.1;
        }

        return false;
    }

    public void act(float delta) {
        if (Gdx.input.isCursorCatched()) {
            Vector3 tmp = new Vector3();

            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                tmp.set(this.camera.direction).nor().scl(delta * this.cameraSpeed);
                this.camera.position.add(tmp);
            }

            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                tmp.set(this.camera.direction).nor().scl(-delta * this.cameraSpeed);
                this.camera.position.add(tmp);
            }

            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                tmp.set(this.camera.direction).crs(this.camera.up).nor().scl(-delta * this.cameraSpeed);
                this.camera.position.add(tmp);
            }

            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                tmp.set(this.camera.direction).crs(this.camera.up).nor().scl(delta * this.cameraSpeed);
                this.camera.position.add(tmp);
            }

            if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
                tmp.set(this.camera.up).nor().scl(delta * this.cameraSpeed);
                this.camera.position.add(tmp);
            }

            if (Gdx.input.isKeyPressed(Input.Keys.E)) {
                tmp.set(this.camera.up).nor().scl(-delta * this.cameraSpeed);
                this.camera.position.add(tmp);
            }

            this.camera.update();
        }

        super.act(delta);
    }
}
