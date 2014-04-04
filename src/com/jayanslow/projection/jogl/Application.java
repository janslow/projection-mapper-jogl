package com.jayanslow.projection.jogl;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collection;
import java.util.LinkedList;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

import com.jayanslow.projection.world.models.CuboidScreen;
import com.jayanslow.projection.world.models.CuboidUniverse;
import com.jayanslow.projection.world.models.FlatScreen;
import com.jayanslow.projection.world.models.RealObject;
import com.jayanslow.projection.world.models.Rotation3f;
import com.jayanslow.projection.world.models.StandardProjector;
import com.jayanslow.projection.world.models.Universe;

public class Application {

	public static void main(String[] args) {

		final Visualiser v = new Visualiser(makeSampleWorld());
		v.setSize(500, 500);
		v.setVisible(true);

		v.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	public static Universe makeSampleWorld() {
		int id = 0;
		final Collection<RealObject> objects = new LinkedList<RealObject>();
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

		return universe;
	}
}
