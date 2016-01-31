public class GameMain {	
	
	public static GameWindow graphics = new GameWindow("Shooting Game",1280,720);
	public static Player player = new Player(0.5f,0.8f,0.008f,0.008f,graphics);
	
	
	public static void main(String[] args) {
		
		FPSManager fps = new FPSManager(60);
		GameManager game = new GameManager("stage.csv");

		graphics.addKeyListener(player);	// keyリスナーを設定する
		graphics.addKeyListener(game);		// debug用キー
		game.player = player;
		
		//メインループ
		while(true){
			//fpsの制御のため処理の最初にタイマーを設定する
			fps.setTimer();
			
			//バッファを更新するためバッファを塗りつぶす
			graphics.clear(0,0,0);
			
			//ゲームの処理を行う
			game.run();

			//現在の処理時間を表示する
			graphics.setColor(255, 0, 255);
			graphics.drawString("ProcessTime:"+String.format("%.3f", fps.getProcessTime())+"ms",20,0.83f,0.99f);
			
			//次回の描画処理中にアンチエリアシングを有効にするか決定する
			if(fps.getProcessTime() - fps.getFrameSec() < 1){
				//アンチエイリアシングを有効にする
				graphics.setAntiAliasing(true);
			}else{
				//アンチエイリアシングを無効にする
				graphics.setAntiAliasing(false);
			}
			
			//FPSを制御するために一定時間処理を中断する
			fps.FPS();
			//現在のPFSを表示する
			graphics.drawString(String.format("%.1f", fps.getFPS())+"FPS",20,0.93f,0.96f);
						
			//バックバッファをチェンジする
			graphics.flip();

			//ロストの確認
			//環境によってバッファが消失する場合がある
			if(graphics.bufferStrategy.contentsLost()){
				System.out.println("バッファロスト");
				//復旧する
				graphics.reCreateBuffer();
			}
		}

	}

}