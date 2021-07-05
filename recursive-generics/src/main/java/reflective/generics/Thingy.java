package reflective.generics;

interface Thingy<T extends Thingy<T>> extends Comparable<T> {}

class Nail implements Thingy<Nail> {
    @Override public int compareTo(Nail o) {
		return 0;  }
}

class Bike implements Thingy<Bike> {
    @Override public int compareTo(Bike o) {
		return 0;  }
}

