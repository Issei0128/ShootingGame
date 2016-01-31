/*
 * 敵のスーパークラス
 * すべての敵はこのクラスを継承する
 */
public class Enemy extends GameObject{
	//加速度
	float vx,vy;
	//出現時間
	int appTime;
	//弾幕タイプ
	int shotType;
	//弾道数
	int bulletWay;
	//弾速度
	float bulletSpeed;
	//削除を管理するフラグ
	boolean checkDelete;
	//発射のタイミングを決めるためのカウンター
	int count = 0;

	Enemy(EnemyData data) {
		super(data.x,data.y,data.deleteFlag);
		this.appTime = data.appTime;
		this.shotType = data.shotType;
		this.vx = data.vx;
		this.vy = data.vy;
		this.hp = data.hp;
		this.bulletWay = data.bulletWay;
		this.bulletSpeed = data.bulletSpeed;
	}
}