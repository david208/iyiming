package com.scnet.iyiming.entity.system;

public abstract class VersionedAuditableIdEntity extends AuditableEntity {
	private static final long serialVersionUID = -1016215252241558312L;

	// 将以下SEQ注解复制到每个子类 且覆盖getId()方法 eg: task class
	// @Id
	// @SequenceGenerator(name = "generator", sequenceName = "SEQ_SS_TASK")
	// @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
	// "generator")

	/**
	 * ******* return parameters is "id" Not return null********
	 * 
	 * @Id
	 * @SequenceGenerator(name = "generator", sequenceName = "SEQ_SS_TASK")
	 * @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
	 *                          "generator")
	 */
	public abstract Long getId();
}
