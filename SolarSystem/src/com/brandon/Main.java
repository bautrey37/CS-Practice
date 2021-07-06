package com.brandon;

import java.util.ArrayList;
import java.util.List;

class SolarSystem {
  CelestialBody rootCelestialBody;

  SolarSystem() {
    initializePlanets();
  }

  private void initializePlanets() {
    CelestialBody phobos = CelestialBody.of("Phobos", null, 5.989, 5f);
    List<CelestialBody> marsMoons = new ArrayList<>();
    marsMoons.add(phobos);
    CelestialBody mars = CelestialBody.of("Mars", marsMoons, 250540.0, 0f);

    CelestialBody earth = CelestialBody.of("Earth", null, 152080.0, 0f);

    CelestialBody titan = CelestialBody.of("Titan", null, 1221.8, 123f);
    List<CelestialBody> saturnMoons = new ArrayList<>();
    saturnMoons.add(titan);
    CelestialBody saturn = CelestialBody.of("Saturn", saturnMoons, 1486900.0, 355.1f);

    List<CelestialBody> bodiesAroundSun = new ArrayList<>();
    bodiesAroundSun.add(mars);
    bodiesAroundSun.add(earth);
    bodiesAroundSun.add(saturn);
    CelestialBody sun = CelestialBody.of("Sun", bodiesAroundSun, 0.0, 0f);

    this.rootCelestialBody = sun;
  }

  /**
   * Finds the distance between the two celestial bodies provided. Solution is not perfect as it measures distance relative to the center object.
   *
   * @param body1 - name of Celestial body number 1
   * @param body2 - name of Celestial body number 2
   * @return distance between Celestial bodies in thousand kilometers
   */
  public double getDistance(String body1, String body2) {
    double[] distance1 = distanceToRoot(rootCelestialBody, body1);
    double[] distance2 = distanceToRoot(rootCelestialBody, body2);

    double[] totalDistance = new double[2];
    totalDistance[0] = distance1[0] + distance2[0];
    totalDistance[1] = distance1[1] + distance2[1];

    return Math.sqrt(Math.pow(totalDistance[1], 2) + Math.pow(totalDistance[0], 2));
  }

  private double[] distanceToRoot(CelestialBody body, String targetName) {
    List<CelestialBody> path = new ArrayList<>();
    findCelestialBodyPath(body, targetName, path);

    double[] distance = new double[2];
    for (CelestialBody curBody : path) {
      calcDistance(distance, curBody.getDistanceToRoot(), curBody.getOrbitalAngle());
    }
    return distance;
  }

  private boolean findCelestialBodyPath(
      CelestialBody curBody, String targetName, List<CelestialBody> path) {
    if (curBody == null) {
      return false;
    }

    if (curBody.getName().equals(targetName)) {
      path.add(curBody);
      return true;
    }

    if (curBody.getRelativeCelestialBodies() != null) {
      for (CelestialBody body : curBody.getRelativeCelestialBodies()) {
        if (findCelestialBodyPath(body, targetName, path)) {
          path.add(curBody);
          return true;
        }
      }
    }

    return false;
  }

  private void calcDistance(double[] originalDistance, Double distanceToRoot, Float orbitalAngle) {
    double orbitalRadians = Math.toRadians(orbitalAngle);
    double xDis = Math.sin(orbitalRadians) * distanceToRoot + originalDistance[0];
    double yDis = Math.cos(orbitalRadians) * distanceToRoot + originalDistance[1];

    originalDistance[0] = xDis;
    originalDistance[1] = yDis;
  }
}

class CelestialBody {
  CelestialBody() {}

  private String name;
  private List<CelestialBody> relativeCelestialBodies;
  private Double distanceToRoot; // thousand kilometer
  private Float orbitalAngle;

  public static CelestialBody of(
      String name,
      List<CelestialBody> relativeCelestialBodies,
      Double distanceToRoot,
      Float orbitalAngle) {
    CelestialBody body = new CelestialBody();
    body.setName(name);
    body.setRelativeCelestialBody(relativeCelestialBodies);
    body.setDistanceToRoot(distanceToRoot);
    body.setOrbitalAngle(orbitalAngle);
    return body;
  }

  private void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  private void setRelativeCelestialBody(List<CelestialBody> relativeCelestialBodies) {
    this.relativeCelestialBodies = relativeCelestialBodies;
  }

  public List<CelestialBody> getRelativeCelestialBodies() {
    return this.relativeCelestialBodies;
  }

  private void setDistanceToRoot(Double distanceToRoot) {
    this.distanceToRoot = distanceToRoot;
  }

  public Double getDistanceToRoot() {
    return this.distanceToRoot;
  }

  private void setOrbitalAngle(Float orbitalAngle) {
    this.orbitalAngle = orbitalAngle;
  }

  public Float getOrbitalAngle() {
    return this.orbitalAngle;
  }
}

// runner, calls the library
public class Main {
  public static void main(String[] args) {

    SolarSystem solarsystem = new SolarSystem();
    System.out.println(
        "Mars and Earth are " + solarsystem.getDistance("Mars", "Earth") + " thousand km apart");
    System.out.println(
        "Phobos and Titan are "
            + solarsystem.getDistance("Phobos", "Titan")
            + " thousand km apart");
    System.out.println(
        "Saturn and Mars are " + solarsystem.getDistance("Saturn", "Mars") + " thousand km apart");
  }
}
