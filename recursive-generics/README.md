

Lets assume you have a class Thingy. You want all subclasses of Thingy to be comparable, but just to the same subclass. Ie you want nails to be comparable to nails, but not bikes:
 
 
interface Thingy<T extends Thingy<T>> extends Comparable<T> {}

class Nail implements Thingy<Nail> {
    @Override public int compareTo(Nail o) { ... }
}

class Bike implements Thingy<Bike> {
    @Override public int compareTo(Bike o) { ... }
}
