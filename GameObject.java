/*
 * ゲームオブジェクトのスーパークラス
 * ゲーム内で生成するオブジェクトはこのクラスを継承する
 */
public class GameObject {
	float x,y;
	int hp;
	boolean deleteFlag;
	GameObject(float x,float y,boolean DeleteFlag){
		this.x = x;
		this.y = y;
		this.deleteFlag = DeleteFlag;
		hp = -1;
	}

	public void process(){};
	public void draw(){};
	
	//画面内に存在するか確認
	boolean checkInside(){
		if(	y < 1 && y > 0 &&
			x < 1 && x > 0 ){
			return true;
		}
		return false;
	}
	
	//画面外に存在するか確認
	boolean checkOutside(){
		if( y > 1 || y < 0 ||
			x > 1 || x < 0){
			return true;
		}
		return false;
	}
}