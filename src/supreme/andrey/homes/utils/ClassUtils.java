package supreme.andrey.homes.utils;

public class ClassUtils {
	public static boolean classExists (String className) {
		try {
			Class.forName(className);
			return true;
		} catch (ClassNotFoundException e) {
			System.out.println("Classe "+className+" não encontrada:"+e);
			return false;
		}
	}
	
	public static boolean isRunningInSpigot () {
		return classExists("org.spigotmc.SpigotConfig");
	}
	public static boolean isRunningInPaper() {
		return classExists("com.destroystokyo.paper.ParticleBuilder");
	}
}
