package evolution;

public class TEST< T extends Foo > {
	public void lol(Foo f){
		T lel = (T)f;
	}
	
	public static void main(String[] args) {
		TEST t = new TEST();
		t.lol(new Foo(){});
	}
}

interface Foo{}
