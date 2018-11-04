import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@interface Table{
    String name();
}

@Retention(RetentionPolicy.RUNTIME)
@interface  Id{

}

@Retention(RetentionPolicy.RUNTIME)
@interface Column{
    String name();
}
