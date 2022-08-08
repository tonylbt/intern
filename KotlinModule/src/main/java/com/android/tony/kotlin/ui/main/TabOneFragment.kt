package com.android.tony.kotlin.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.android.tony.kotlin.R
import com.android.tony.kotlin.grammar.GrammarArray
import com.android.tony.kotlin.grammar.GrammarBasics
import com.android.tony.kotlin.grammar.GrammarBasics.UpdateListener
import com.android.tony.kotlin.utils.NotifyHelper
import com.google.android.material.snackbar.Snackbar

class TabOneFragment : Fragment() {
    private lateinit var mPageTitle : TextView
    private lateinit var mBtn1 : Button
    private lateinit var mTextContent : TextView
    private lateinit var mCurrentContent : String

    private lateinit var basicsGrammar: GrammarBasics
    private lateinit var mUpdateListener : UpdateListener

    private lateinit var arrayGrammar : GrammarArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view : View = inflater.inflate(R.layout.fragment_tab_one_layout, container, false)

        initView(view)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()

        exec()
    }

    private fun initView(view : View) {
        mPageTitle = view.findViewById(R.id.tv_page_one_title)
        val idx : Int = arguments?.getInt(CURRENT_FRAGMENT_INDEX) ?: 1
        mPageTitle.text = "This is Page $idx title."
        mBtn1 = view.findViewById(R.id.btn_page_one_1)
        mBtn1.setOnClickListener(mOnClickListener)
        view.findViewById<Button>(R.id.btn_page_one_2).setOnClickListener(mOnClickListener)
        view.findViewById<Button>(R.id.btn_page_one_3).setOnClickListener(mOnClickListener)
        mTextContent = view.findViewById(R.id.tv_page_one_content)
    }

    private fun initData() {
        mUpdateListener = object : UpdateListener {
            override fun onUpdateContent(content: String) {
                mCurrentContent += content
                mTextContent.text = mCurrentContent
            }
        }
        basicsGrammar = GrammarBasics()
        basicsGrammar.setUpdateListener(mUpdateListener)

        arrayGrammar = GrammarArray()
    }

    private fun exec() {

    }

    private val mOnClickListener = View.OnClickListener {
        if (it.id == R.id.btn_page_one_1) {
            mCurrentContent = ""
            basicsGrammar.mainEntrance()
        } else if (it.id == R.id.btn_page_one_2) {
            arrayGrammar.mainMethod()
        } else if (it.id == R.id.btn_page_one_3) {
            execNotify()
        }
        Snackbar.make(it, "You clicked page ${getCurrentPageIndex()} Button :P", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
    }

    private fun execNotify() {
        val url = "libertys://hybrid/46?game_earn_url=http://cdn.shareitgames.com/game-earn/index.html%23/%3Fearnportal=push%26portal=push%26"

        val intent = Intent.parseUri(url, 0)
//        startActivity(intent)

        var ctx = context
        ctx?.let {
            NotifyHelper().showNotification(it, "Come to play funny games", "Play game until you win.", "", intent, "push", 1000, true)
        }
    }

    private fun getCurrentPageIndex() : Int {
        return arguments?.getInt(CURRENT_FRAGMENT_INDEX) ?: 1
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val CURRENT_FRAGMENT_INDEX = "current_index"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): TabOneFragment {
            return TabOneFragment().apply {
                arguments = Bundle().apply {
                    putInt(CURRENT_FRAGMENT_INDEX, sectionNumber)
                }
            }
        }
    }
}