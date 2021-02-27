// Copyright (c) 2021 PSForever
package objects

import net.psforever.objects.geometry._
import net.psforever.types.Vector3
import org.specs2.mutable.Specification

class GeometryTest extends Specification {
  "Point3D" should {
    "construct (1)" in {
      Point3D(1,2,3.5f)
      ok
    }

    "construct (2)" in {
      Point3D() mustEqual Point3D(0,0,0)
    }

    "construct (3)" in {
      Point3D(Vector3(1,2,3)) mustEqual Point3D(1,2,3)
    }

    "be its own center point" in {
      val obj = Point3D(1,2,3.5f)
      obj.center mustEqual obj
    }

    "define its own exterior" in {
      val obj = Point3D(1,2,3.5f)
      obj.pointOnOutside(Vector3(1,0,0)) mustEqual obj
      obj.pointOnOutside(Vector3(0,1,0)) mustEqual obj
      obj.pointOnOutside(Vector3(0,0,1)) mustEqual obj
    }

    "convert to Vector3" in {
      val obj = Point3D(1,2,3.5f)
      obj.asVector3 mustEqual Vector3(1,2,3.5f)
    }
  }

  "Ray3D" should {
    "construct (1)" in {
      Ray3D(Point3D(1,2,3.5f), Vector3(1,0,0))
      ok
    }

    "construct (2)" in {
      Ray3D(1,2,3.5f, Vector3(1,0,0)) mustEqual Ray3D(Point3D(1,2,3.5f), Vector3(1,0,0))
    }

    "construct (3)" in {
      Ray3D(Vector3(1,2,3.5f), Vector3(1,0,0)) mustEqual Ray3D(Point3D(1,2,3.5f), Vector3(1,0,0))
    }

    "have a unit vector as its direction vector" in {
      Ray3D(1,2,3.5f, Vector3(1,1,1)) must throwA[AssertionError]
    }

    "have its target point as the center point" in {
      val obj = Ray3D(1,2,3.5f, Vector3(1,0,0))
      obj.center mustEqual Point3D(1,2,3.5f)
    }

    "define its own exterior" in {
      val obj1 = Ray3D(1,2,3.5f, Vector3(1,0,0))
      val obj2 = Point3D(1,2,3.5f)
      obj1.pointOnOutside(Vector3(1,0,0)) mustEqual obj2
      obj1.pointOnOutside(Vector3(0,1,0)) mustEqual obj2
      obj1.pointOnOutside(Vector3(0,0,1)) mustEqual obj2
    }
  }

  "Line3D" should {
    "construct (1)" in {
      Line3D(Point3D(1,2,3.5f), Vector3(1,0,0))
      ok
    }

    "construct (2)" in {
      Line3D(1,2,3.5f, Vector3(1,0,0))
      ok
    }

    "construct (3)" in {
      Line3D(1,2,3.5f, 2,2,3.5f) mustEqual Line3D(1,2,3.5f, Vector3(1,0,0))
    }

    "have a unit vector as its direction vector" in {
      Line3D(1,2,3.5f, Vector3(1,1,1)) must throwA[AssertionError]
    }

    "have its target point as the center point" in {
      val obj = Line3D(1,2,3.5f, Vector3(1,0,0))
      obj.center mustEqual Point3D(1,2,3.5f)
    }

    "define its own exterior" in {
      val obj1 = Line3D(1,2,3.5f, Vector3(1,0,0))
      val obj2 = Point3D(1,2,3.5f)
      obj1.pointOnOutside(Vector3(1,0,0)) mustEqual obj2
      obj1.pointOnOutside(Vector3(0,1,0)) mustEqual obj2
      obj1.pointOnOutside(Vector3(0,0,1)) mustEqual obj2
    }
  }

  "Segment3D" should {
    "construct (1)" in {
      Segment3D(Point3D(1,2,3), Point3D(3,2,3))
      ok
    }

    "construct (2)" in {
      Segment3D(1,2,3, 3,2,3) mustEqual Segment3D(Point3D(1,2,3), Point3D(3,2,3))
      ok
    }

    "construct (3)" in {
      Segment3D(Point3D(1,2,3), Vector3(1,0,0)) mustEqual Segment3D(Point3D(1,2,3), Point3D(2,2,3))
    }

    "construct (4)" in {
      Segment3D(1,2,3, Vector3(1,0,0)) mustEqual Segment3D(Point3D(1,2,3), Point3D(2,2,3))
    }

    "does not need to have unit vector as its direction vector" in {
      val obj1 = Segment3D(1,2,3, Vector3(5,1,1))
      val obj2 = Segment3D(Point3D(1,2,3), Point3D(6,3,4))
      obj1 mustEqual obj2
      obj1.d mustEqual obj2.d
    }

    "have a midway point between its two endpoints" in {
      Segment3D(Point3D(1,2,3), Point3D(3,4,5)).center mustEqual Point3D(2,3,4)
    }

    "report the point on the outside as its center point" in {
      val obj1 = Segment3D(Point3D(1,2,3), Point3D(3,4,5))
      val obj2 = obj1.center
      obj1.pointOnOutside(Vector3(1,0,0)) mustEqual obj2
      obj1.pointOnOutside(Vector3(0,1,0)) mustEqual obj2
      obj1.pointOnOutside(Vector3(0,0,1)) mustEqual obj2
    }
  }

  "Sphere3D" should {
    "construct (1)" in {
      Sphere(Point3D(1,2,3), 3)
      ok
    }

    "construct (2)" in {
      Sphere(3) mustEqual Sphere(Point3D(0,0,0), 3)
      ok
    }

    "construct (3)" in {
      Sphere(1,2,3, 3) mustEqual Sphere(Point3D(1,2,3), 3)
    }

    "construct (4)" in {
      Sphere(Vector3(1,2,3), 3) mustEqual Sphere(Point3D(1,2,3), 3)
    }

    "the center point is self-evident" in {
      Sphere(Point3D(1,2,3), 3).center mustEqual Point3D(1,2,3)
    }

    "report the point on the outside depending on the requested direction" in {
      val obj1 = Sphere(1,2,3, 3)
      obj1.pointOnOutside(Vector3( 1, 0, 0)) mustEqual Point3D( 4, 2,3) //east
      obj1.pointOnOutside(Vector3( 0, 1, 0)) mustEqual Point3D( 1, 5,3) //north
      obj1.pointOnOutside(Vector3( 0, 0, 1)) mustEqual Point3D( 1, 2,6) //up
      obj1.pointOnOutside(Vector3(-1, 0, 0)) mustEqual Point3D(-2, 2,3) //west
      obj1.pointOnOutside(Vector3( 0,-1, 0)) mustEqual Point3D( 1,-1,3) //south
      obj1.pointOnOutside(Vector3( 0, 0,-1)) mustEqual Point3D( 1, 2,0) //down
    }
  }

  "Cylinder (normal)" should {
    "construct (1)" in {
      Cylinder(Point3D(1,2,3), Vector3(0,0,1), 2, 3)
      ok
    }

    "construct (2)" in {
      Cylinder(Point3D(1,2,3), 2, 3) mustEqual Cylinder(Point3D(1,2,3), Vector3(0,0,1), 2, 3)
    }

    "construct (3)" in {
      Cylinder(Vector3(1,2,3), 2, 3) mustEqual Cylinder(Point3D(1,2,3), Vector3(0,0,1), 2, 3)
    }

    "construct (4)" in {
      Cylinder(Vector3(1,2,3), Vector3(0,0,1), 2, 3) mustEqual Cylinder(Point3D(1,2,3), Vector3(0,0,1), 2, 3)
    }

    "report the center point as the center of the cylinder" in {
      Cylinder(Point3D(1,2,3), 2, 3).center mustEqual Point3D(1,2,4.5f)
    }

    "the point on the outside is different depending on the requested direction" in {
      val obj1 = Cylinder(Point3D(1,2,3), 2, 3)
      obj1.pointOnOutside(Vector3( 1, 0, 0)) mustEqual Point3D( 3, 2, 4.5f) //east
      obj1.pointOnOutside(Vector3( 0, 1, 0)) mustEqual Point3D( 1, 4, 4.5f) //north
      obj1.pointOnOutside(Vector3( 0, 0, 1)) mustEqual Point3D( 1, 2,   6f) //up
      obj1.pointOnOutside(Vector3(-1, 0, 0)) mustEqual Point3D(-1, 2, 4.5f) //west
      obj1.pointOnOutside(Vector3( 0,-1, 0)) mustEqual Point3D( 1, 0, 4.5f) //south
      obj1.pointOnOutside(Vector3( 0, 0,-1)) mustEqual Point3D( 1, 2,   3f) //down
    }
  }

  "Cylinder (side tilt)" should {
    "not require a specific direction to be relative up" in {
      Cylinder(Point3D(1,2,3), Vector3(1,0,0), 2, 3)
      ok
    }

    "require its specific relative up direction to be expressed as a unit vector" in {
      Cylinder(Point3D(1,2,3), Vector3(4,0,0), 2, 3) must throwA[AssertionError]
    }

    "report the center point as the center of the cylinder, as if rotated about its base" in {
      Cylinder(Point3D(1,2,3), Vector3(1,0,0), 2, 3).center mustEqual Point3D(2.5f, 2, 3)
    }

    "report the point on the outside as different depending on the requested direction and the relative up direction" in {
      val obj1 = Cylinder(Point3D(1,2,3), Vector3(1,0,0), 2, 3)
      obj1.pointOnOutside(Vector3( 1, 0, 0)) mustEqual Point3D(4,    2, 3) //east
      obj1.pointOnOutside(Vector3( 0, 1, 0)) mustEqual Point3D(2.5f, 4, 3) //north
      obj1.pointOnOutside(Vector3( 0, 0, 1)) mustEqual Point3D(2.5f, 2, 5) //up
      obj1.pointOnOutside(Vector3(-1, 0, 0)) mustEqual Point3D(1,    2, 3) //west
      obj1.pointOnOutside(Vector3( 0,-1, 0)) mustEqual Point3D(2.5f, 0, 3) //south
      obj1.pointOnOutside(Vector3( 0, 0,-1)) mustEqual Point3D(2.5f, 2, 1) //down

      val obj2 = Cylinder(Point3D(1,2,3), Vector3(0,0,1), 2, 3)
      obj1.pointOnOutside(Vector3( 1, 0, 0)) mustNotEqual obj2.pointOnOutside(Vector3( 1, 0, 0))
      obj1.pointOnOutside(Vector3( 0, 1, 0)) mustNotEqual obj2.pointOnOutside(Vector3( 1, 1, 0))
      obj1.pointOnOutside(Vector3( 0, 0, 1)) mustNotEqual obj2.pointOnOutside(Vector3( 1, 0, 1))
      obj1.pointOnOutside(Vector3(-1, 0, 0)) mustNotEqual obj2.pointOnOutside(Vector3(-1, 0, 0))
      obj1.pointOnOutside(Vector3( 0,-1, 0)) mustNotEqual obj2.pointOnOutside(Vector3( 0,-1, 0))
      obj1.pointOnOutside(Vector3( 0, 0,-1)) mustNotEqual obj2.pointOnOutside(Vector3( 0, 0,-1))
    }
  }
}
