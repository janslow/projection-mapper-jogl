package com.jayanslow.projection.jogl;

import java.awt.Color;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

import com.jayanslow.projection.texture.controllers.MapTextureController;
import com.jayanslow.projection.texture.controllers.TextureController;
import com.jayanslow.projection.texture.models.ColorImageTexture;
import com.jayanslow.projection.texture.models.FileImageTexture;
import com.jayanslow.projection.texture.models.ImageTexture;
import com.jayanslow.projection.texture.models.ListVideoTexture;
import com.jayanslow.projection.texture.models.PreviewTexture;
import com.jayanslow.projection.texture.models.Texture;
import com.jayanslow.projection.world.controllers.HashMapWorldController;
import com.jayanslow.projection.world.controllers.WorldController;
import com.jayanslow.projection.world.models.CuboidScreen;
import com.jayanslow.projection.world.models.CuboidUniverse;
import com.jayanslow.projection.world.models.Face;
import com.jayanslow.projection.world.models.FlatScreen;
import com.jayanslow.projection.world.models.RealObject;
import com.jayanslow.projection.world.models.RectangularFace;
import com.jayanslow.projection.world.models.Rotation3f;
import com.jayanslow.projection.world.models.Screen;
import com.jayanslow.projection.world.models.ScreenType;
import com.jayanslow.projection.world.models.StandardProjector;
import com.jayanslow.projection.world.models.Universe;

public class SampleWorld {

	public static TextureController makeSampleTextures(WorldController world) {
		TextureController textures = new MapTextureController(new HashMap<Face, Texture>());
	
		Random r = new Random();
		for (Screen s : world.getScreens())
			if (s.getScreenType().equals(ScreenType.FLAT)) {
				RectangularFace f = (RectangularFace) s.getFace(0);
				Vector2f dimensions = f.getDimensions();
	
				List<ImageTexture> images = new LinkedList<>();
				Collections.addAll(images, new FileImageTexture("/Users/jay/Pictures/robot1.jpg"),
						new ColorImageTexture(Color.RED, dimensions), new ColorImageTexture(Color.GREEN, dimensions),
						new ColorImageTexture(Color.BLUE, dimensions));
				PreviewTexture preview = new PreviewTexture(textures, f, new ListVideoTexture(images));
				textures.putTexture(f, preview);
				preview.preview(new ColorImageTexture(Color.RED));
			} else
				for (Face f : s.getFaces()) {
					Color c = Color.getHSBColor(r.nextFloat(), 0.55f, 0.76f);
					List<ImageTexture> ts = Arrays.asList((ImageTexture) new FileImageTexture(
							"/Users/jay/Pictures/robot1.jpg"),
							new ColorImageTexture(c, ((RectangularFace) f).getDimensions()));
					textures.putTexture(f, new ListVideoTexture(ts));
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
