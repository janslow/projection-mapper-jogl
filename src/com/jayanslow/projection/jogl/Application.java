package com.jayanslow.projection.jogl;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

import com.jayanslow.projection.texture.controllers.MapTextureController;
import com.jayanslow.projection.texture.controllers.TextureController;
import com.jayanslow.projection.texture.models.BufferedImageTexture;
import com.jayanslow.projection.texture.models.Texture;
import com.jayanslow.projection.world.controllers.HashMapWorldController;
import com.jayanslow.projection.world.controllers.WorldController;
import com.jayanslow.projection.world.editor.controller.WorldEditorController;
import com.jayanslow.projection.world.editor.controller.StandardWorldEditorController;
import com.jayanslow.projection.world.models.CuboidScreen;
import com.jayanslow.projection.world.models.CuboidUniverse;
import com.jayanslow.projection.world.models.Face;
import com.jayanslow.projection.world.models.FlatScreen;
import com.jayanslow.projection.world.models.RealObject;
import com.jayanslow.projection.world.models.RectangularFace;
import com.jayanslow.projection.world.models.Rotation3f;
import com.jayanslow.projection.world.models.Screen;
import com.jayanslow.projection.world.models.StandardProjector;
import com.jayanslow.projection.world.models.Universe;

public class Application {

	private static BufferedImageTexture createImage(Color color, Vector2f dimensions) {
		int x = 1000, y = (int) (x / dimensions.x * dimensions.y);
		BufferedImage image = new BufferedImage(x, y, BufferedImage.TYPE_3BYTE_BGR);

		Graphics2D g = image.createGraphics();
		g.setPaint(color);
		g.fillRect(0, 0, x, y);

		return new BufferedImageTexture(image);
	}

	public static void main(String[] args) {
		WorldController world = makeSampleWorld();
		TextureController textures = makeSampleTextures(world);

		final MasterVisualiser v = new MasterVisualiser(world, textures, 500, 500);
		v.setVisible(true);

		v.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		final ProjectorVisualiser p1 = new ProjectorVisualiser(world, textures, world.getProjector(0),
				RenderMode.TEXTURED);
		p1.setVisible(true);

		final WorldEditorController c = new StandardWorldEditorController(world);
		c.editUniverse();
	}

	public static TextureController makeSampleTextures(WorldController world) {
		TextureController textures = new MapTextureController(new HashMap<Face, Texture>());

		Random r = new Random();
		for (Screen s : world.getScreens())
			for (Face f : s.getFaces()) {
				Color c = Color.getHSBColor(r.nextFloat(), 0.55f, 0.76f);
				textures.putTexture(f, createImage(c, ((RectangularFace) f).getDimensions()));
			}
		return textures;
	}

	public static WorldController makeSampleWorld() {
		int id = 0;
		final List<RealObject> objects = new LinkedList<RealObject>();
		final Universe universe = new CuboidUniverse(new Vector3f(5000, 5000, 5000), objects);

		int projector = 0;
		objects.add(new StandardProjector(id++, projector++, new Vector3f(2500, 4000, 0), new Rotation3f(
				(float) Math.PI / 6, 0, 0), new Vector3f(300, 160, 450), 768, 1024, 3));
		objects.add(new StandardProjector(id++, projector++, new Vector3f(0, 5000, 3000), new Rotation3f(
				(float) Math.PI / 4, (float) Math.PI / 4, 0), new Vector3f(300, 160, 450), 768, 1024, 1));

		int screen = 0;
		objects.add(new FlatScreen(id++, screen++, new Vector3f(4500, 500, 5000),
				new Rotation3f(0, (float) Math.PI, 0), new Vector2f(4000, 4000)));
		objects.add(new CuboidScreen(id++, screen++, new Vector3f(2000, 0, 3500), new Rotation3f(0,
				(float) Math.PI / 4, 0), new Vector3f(1000, 1000, 1000)));

		return new HashMapWorldController(universe);
	}
}
