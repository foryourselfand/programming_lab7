package DataBase.UrlGetter;

public class UrlGetterDirectly extends UrlGetter {
	@Override
	public String getUrl() {
		return "jdbc:postgresql://pg:5432/studs";
	}
}
