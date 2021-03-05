package cheatsheets;

public class KotlinUsage {
    int x = Annotations.getJvmStaticField();
    int y = Annotations.Companion.getKotlinStaticField();

    int z = new Annotations().jvmPublic;
    int a = new Annotations().getValue();
}
