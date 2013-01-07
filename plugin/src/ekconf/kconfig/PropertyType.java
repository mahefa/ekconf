/*
* Ekconf, an Eclipe plug-in for configuring the Linux kernel or Buildroot.
* 
* Copyright (C) 2012 Tiana Rakotovao Andriamahefa <rkmahefa@gmail.com>
* 
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation; either version 2
* of the License, or (at your option) any later version.
* 
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
* 
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 52 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/

package ekconf.kconfig;
/**
 * 
 * @author Tiana Rakotovao
 *
 */
public enum PropertyType {
	 P_UNKNOWN,
	 P_PROMPT,  
	 P_COMMENT, 
	 P_MENU,    
	 P_DEFAULT, 
	 P_CHOICE,  
	 P_SELECT,  
	 P_RANGE,   
	 P_ENV;     
	 
	 private PropertyType(){}
	 
 	public static PropertyType valueOf(int position)
	 {
		 for (PropertyType p : PropertyType.values()) {
			 if (position == p.ordinal())
				 return p;
		 }
		 return null;
	 }
	 
}
