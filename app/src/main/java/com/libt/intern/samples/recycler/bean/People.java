package com.libt.intern.samples.recycler.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by qwe on 2018/8/7.
 */

public class People extends DataSupport {
	private int id;
	private String item;
	private int leftflag;
	private int rightflag;
	private int leftall;
	private int rightall;
	private String name1;
	private String name2;
	private int level1;
	private int level2;
	private String image1;
	private String image2;
	private String tx1;
	private String tx2;
	public People() {}
	public People(String name1, String name2, int level1, int level2, String image1, String image2, String tx1, String tx2) {
		this.name1 = name1;
		this.name2 = name2;
		this.image1 = image1;
		this.image2 = image2;
		this.level1 = level1;
		this.level2 = level2;
		this.tx1 = tx1;
		this.tx2 = tx2;
	}
	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLeftflag() {
		return leftflag;
	}

	public void setLeftflag(int leftflag) {
		this.leftflag = leftflag;
	}

	public int getRightflag() {
		return rightflag;
	}

	public void setRightflag(int rightflag) {
		this.rightflag = rightflag;
	}
	public int getLeftall() {
		return leftall;
	}

	public void setLeftall(int leftall) {
		this.leftall = leftall;
	}

	public int getRightall() {
		return rightall;
	}

	public void setRightall(int rightall) {
		this.rightall = rightall;
	}

	public String getName1() {
		return name1;
	}
	public String getName2() {
		return name2;
	}
	public int getLevel1() {
		return level1;
	}
	public int getLevel2() {
		return level2;
	}
	public String getImage1() {
		return image1;
	}
	public String getImage2() {
		return image2;
	}
	public String getTx1() {
		return tx1;
	}
	public String getTx2() {
		return tx2;
	}

}
