package com.example.musicplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.Activity
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.CompoundButton
import android.widget.Switch
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity() {

    val mediaPlayer = MediaPlayer()
    lateinit var btnPlay: Button
    lateinit var btnStop: Button
    lateinit var swLoopOn: Switch

    val getContentSound =
        registerForActivityResult(StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val resultIntent = it.data
                val uri: Uri? = resultIntent?.data

                mediaPlayer.apply {
                    stop()
                    reset()
                }

                if (uri != null) {
                    mediaPlayer.apply {
                        setDataSource(this@MainActivity, uri) // 音源を設定
                        // メディアソースの再生準備が整った時に呼び出されるコールバックの登録をする
                        setOnPreparedListener {
                            // 各ボタンをタップに可能に設定
                            btnPlay.setEnabled(true)
                            btnPlay.text = getString(R.string.play)
                            btnStop.setEnabled(true)
                        }

                        // 再生中にメディアソースの終端に到達した時に呼び出されるコールバックを登録
                        setOnCompletionListener {
                            // ループ設定がされていなければ
                            if (!isLooping()) {
                                // 再生ボタンのラベルを「再生」に設定
                                btnPlay.text = getString(R.string.play)
                            }
                        }
                        prepareAsync()
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnPlay = findViewById<Button>(R.id.btnPlay)
        btnStop = findViewById<Button>(R.id.btnStop)
        swLoopOn = findViewById<Switch>(R.id.swLoop)

        btnPlay.setOnClickListener {
            if(!mediaPlayer.isPlaying) {
                try {
                    mediaPlayer.start()
                    btnPlay.text = getString(R.string.pause)
                } catch (e:IllegalStateException) {
                    e.printStackTrace()
                }
            } else {
                try {
                    //再生を一時停止
                    mediaPlayer.pause()
                } catch (e:IllegalStateException) {
                    e.printStackTrace()
                }
                btnPlay.text = getString(R.string.play)
            }
        }

        btnStop.setOnClickListener {
            try {
                mediaPlayer.stop()
                mediaPlayer.prepare()
            } catch (e: IllegalStateException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            btnPlay.text = getString(R.string.play)
        }

        // ループスイッチの状態が変化した時に、MediaPlayerのインスタンスに反映
        swLoopOn.setOnCheckedChangeListener(
            object : CompoundButton.OnCheckedChangeListener {
                override fun onCheckedChanged (
                    buttonView: CompoundButton?,
                    isChecked: Boolean,
                ) {
                    mediaPlayer.isLooping = isChecked
                }
            }
        )
    }

    // アプリを一時的に隠した時の処理
    override fun onPause() {
        super.onPause()
        try {
            mediaPlayer.pause()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // オプションメニュー用xmlファイルをインフレートする
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // 選択されたメニューのIDのR値による処理分岐
        when (item.itemId) {
            R.id.openSoundFolder -> {
                // ファイルピッカーを呼び出す
                val iSound = Intent(Intent.ACTION_OPEN_DOCUMENT)
                iSound.type = "audio/mpeg"
                iSound.putExtra(Intent.EXTRA_TITLE, "memo.mp3")
                getContentSound.launch(iSound)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}