/*
 * Copyright (c) 2011-2015, fortiss GmbH.
 * Licensed under the Apache License, Version 2.0.
 *
 * Use, modification and distribution are subject to the terms specified
 * in the accompanying license file LICENSE.txt located at the root directory
 * of this software distribution.
 */

package org.fortiss.powerflowsim.importers;

import java.util.HashSet;
import java.util.Set;

import org.fortiss.powerflowsim.model.Model;
import org.fortiss.powerflowsim.model.ModelUtils;

import CIM15.IEC61970.Core.BaseVoltage;
import CIM15.IEC61970.Topology.TopologicalNode;

public class TopologyGenerator extends Importer {

	public Model generateLineDistributionSystem(Integer consumers) {
		Model model = new Model();
		initPackages();

		// create swing bus
		BaseVoltage baseVoltage240 = ModelUtils.createBaseVoltage(model, 240);
		TopologicalNode swingNode = ModelUtils.createSlackBus(model, baseVoltage240);
		model.setSWINGNode(swingNode);
		model = createLineWithHouses(model, swingNode, consumers, baseVoltage240);
		// model = ModelOperations.createTopologicalNodes(model);

		return model;
	}

	public Model generateRadialDistributionSystem(Integer consumers_per_star_line, Integer star_lines) {
		Model model = new Model();
		initPackages();

		// create swing bus
		BaseVoltage baseVoltage240 = ModelUtils.createBaseVoltage(model, 240);
		TopologicalNode swingNode = ModelUtils.createSlackBus(model, baseVoltage240);
		model.setSWINGNode(swingNode);
		for (int i = 0; i < star_lines; i++) {
			model = createLineWithHouses(model, swingNode, consumers_per_star_line, baseVoltage240);
		}
		return model;
	}

	public Model generateMixedFullMeshDistributionSystem(Integer consumers) {
		Model model = new Model();
		initPackages();

		// create swing bus
		BaseVoltage baseVoltage240 = ModelUtils.createBaseVoltage(model, 240);
		TopologicalNode swingNode = ModelUtils.createSlackBus(model, baseVoltage240);
		model.setSWINGNode(swingNode);

		Set<TopologicalNode> houses = new HashSet<TopologicalNode>();

		for (int i = 0; i < consumers; i++) {
			if (i % 2 == 0) {
				houses.add(ModelUtils.createPQBus(model, baseVoltage240, 300, 0));
			} else {
				houses.add(ModelUtils.createPVBus(model, baseVoltage240, 200, 240, 0));
			}
		}

		for (TopologicalNode house : houses) {
			for (TopologicalNode destHouse : houses) {
				if (!house.equals(destHouse)) {
					ModelUtils.createLine(model, house, destHouse, 0.05403, 0.22304);
				}
			}
		}

		ModelUtils.createLine(model, swingNode, (TopologicalNode) houses.toArray()[0], 0.05403, 0.22304);

		return model;
	}

	public Model generateConsumerFullMeshDistributionSystem(Integer consumers) {
		Model model = new Model();
		initPackages();

		// create swing bus
		BaseVoltage baseVoltage240 = ModelUtils.createBaseVoltage(model, 240);
		TopologicalNode swingNode = ModelUtils.createSlackBus(model, baseVoltage240);
		model.setSWINGNode(swingNode);

		Set<TopologicalNode> houses = new HashSet<TopologicalNode>();

		for (int i = 0; i < consumers; i++) {

			houses.add(ModelUtils.createPQBus(model, baseVoltage240, 300, 0));

		}

		for (TopologicalNode house : houses) {
			for (TopologicalNode destHouse : houses) {
				if (!house.equals(destHouse)) {
					ModelUtils.createLine(model, house, destHouse, 0.05403, 0.22304);
				}
			}
		}

		ModelUtils.createLine(model, swingNode, (TopologicalNode) houses.toArray()[0], 0.05403, 0.22304);

		return model;
	}

	private Model createLineWithHouses(Model model, TopologicalNode startNode, Integer houses, BaseVoltage baseVoltage) {

		TopologicalNode lastNode = startNode;

		for (int i = 0; i < houses; i++) {

			TopologicalNode newNode = ModelUtils.createPQBus(model, baseVoltage, -300, 0);
			ModelUtils.createLine(model, lastNode, newNode, (float) 0.05403, (float) 0.22304);

			lastNode = newNode;
		}

		return model;

	}

	public Model generateMeshDistributionSystem(Integer consumers) {
		Model model = new Model();
		return model;
	}

}
