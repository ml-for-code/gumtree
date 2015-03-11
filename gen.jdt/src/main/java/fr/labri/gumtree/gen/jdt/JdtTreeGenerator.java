/*
 * Copyright 2011 Jean-Rémy Falleri
 * 
 * This file is part of Praxis.
 * Praxis is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Praxis is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Praxis.  If not, see <http://www.gnu.org/licenses/>.
 */

package fr.labri.gumtree.gen.jdt;

import fr.labri.gumtree.tree.Tree;


public class JdtTreeGenerator extends AbstractJdtTreeGenerator {

	@Override
	public boolean handleFile(String file) {
		return file.endsWith(".java");
	}

	@Override
	public String getName() {
		return "java-jdt-gt";
	}

	@Override
	protected AbstractJdtVisitor createVisitor() {
		return new JdtVisitor();
	}

	@Override
	public Tree generate(String source, String file) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
