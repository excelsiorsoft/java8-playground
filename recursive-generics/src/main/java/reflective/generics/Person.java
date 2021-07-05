package reflective.generics;

public class Person {

    private String firstName;
    private String lastName;
    private String city;
    private long id;
    protected Person(PersonBuilder builder){
        this.firstName=builder.firstName;
        this.lastName=builder.lastName;
        this.city=builder.city;
        this.id=builder.id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCity() {
        return city;
    }

    public long getAadharId() {
        return id;
    }

    public static class PersonBuilder<SELF extends PersonBuilder<SELF>>{
        private String firstName;
        private String lastName;
        private String city;
        private long id;

        public PersonBuilder(String firstName,String lastName){
            this.firstName=firstName;
            this.lastName=lastName;
        }
        public SELF withCity(String city){
            this.city=city;
            return (SELF)this;
        }
        public SELF withId(long id){
            this.id=id;
            return (SELF)this;
        }
        public Person build(){
            Person person=new Person(this);

            return person;

        }
    }
    
    
    public static class Student extends Person {
        private String degree;
        private String hobby;

        public String getDegree(){
            return degree;
        }

        public String getHobby() {
            return hobby;
        }
        protected Student(StudentBuilder builder){
            super(builder);
            this.degree=builder.degree;
            this.hobby=builder.hobby;

        }
        public static class StudentBuilder extends PersonBuilder<StudentBuilder>{
            private String degree;
            private String hobby;
            public StudentBuilder (String firstName,String lastName,String degree){
                super(firstName,lastName);
                this.degree=degree;
            }
            public StudentBuilder withHobby(String hobby){
                this.hobby=hobby;
                return this;
            }
             public Student build(){
                Student student=new Student(this);
                return student;
            }

        }
    }

}
