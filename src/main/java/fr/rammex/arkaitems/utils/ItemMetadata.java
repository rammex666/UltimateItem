package fr.rammex.arkaitems.utils;

import java.lang.reflect.Field;
import java.util.List;

import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;

import com.google.common.collect.Lists;

import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.NBTBase;
import net.minecraft.server.v1_8_R3.NBTTagByte;
import net.minecraft.server.v1_8_R3.NBTTagByteArray;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagDouble;
import net.minecraft.server.v1_8_R3.NBTTagEnd;
import net.minecraft.server.v1_8_R3.NBTTagFloat;
import net.minecraft.server.v1_8_R3.NBTTagInt;
import net.minecraft.server.v1_8_R3.NBTTagIntArray;
import net.minecraft.server.v1_8_R3.NBTTagList;
import net.minecraft.server.v1_8_R3.NBTTagLong;
import net.minecraft.server.v1_8_R3.NBTTagShort;
import net.minecraft.server.v1_8_R3.NBTTagString;

public class ItemMetadata {

    public static org.bukkit.inventory.ItemStack setMetadata(org.bukkit.inventory.ItemStack item, String key, String value) {
        ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        if (nmsItem.getTag() == null) {
            nmsItem.setTag(new NBTTagCompound());
        }
        nmsItem.getTag().setString(key, value);
        return CraftItemStack.asBukkitCopy(nmsItem);
    }

    public static boolean hasMetadata(org.bukkit.inventory.ItemStack item, String key) {
        ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        return nmsItem.getTag() != null && nmsItem.getTag().hasKey(key);
    }

    public static String getMetadata(org.bukkit.inventory.ItemStack item, String key) {
        ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        if (nmsItem.getTag() != null && nmsItem.getTag().hasKey(key)) {
            return nmsItem.getTag().getString(key);
        }
        return null;
    }

    private static NBTTagCompound setTag(NBTTagCompound tag, String tagString, Object value) {
        NBTBase base = null;

        if (value instanceof Boolean) {
            base = new NBTTagByte((byte)(((Boolean)value).booleanValue() ? 1 : 0));
        } else if (value instanceof Integer) {
            base = new NBTTagInt((Integer) value);
        } else if (value instanceof Byte) {
            base = new NBTTagByte((Byte) value);
        } else if (value instanceof Double) {
            base = new NBTTagDouble((Double) value);
        } else if (value instanceof Float) {
            base = new NBTTagFloat((Float) value);
        } else if (value instanceof String) {
            base = new NBTTagString((String) value);
        } else if (value instanceof Short) {
            base = new NBTTagShort((Short) value);
        } else if (value instanceof Long) {
            base = new NBTTagLong((Long) value);
        }

        if(base != null){
            tag.set(tagString, base);
        }

        return tag;
    }

    @SuppressWarnings("unchecked")
    private static Object getObject(NBTBase tag){
        if(tag instanceof NBTTagEnd){
            return null;
        }else if(tag instanceof NBTTagByte){
            return ((NBTTagByte) tag).f();
        }else if(tag instanceof NBTTagShort){
            return ((NBTTagShort) tag).e();
        }else if(tag instanceof NBTTagInt){
            return ((NBTTagInt) tag).e();
        }else if(tag instanceof NBTTagLong){
            return ((NBTTagLong) tag).d();
        }else if(tag instanceof NBTTagFloat){
            return ((NBTTagFloat) tag).h();
        }else if(tag instanceof NBTTagDouble){
            return ((NBTTagDouble) tag).h();
        }else if(tag instanceof NBTTagByteArray){
            return ((NBTTagByteArray) tag).c();
        }else if(tag instanceof NBTTagString){
            return ((NBTTagString) tag).a_();
        }else if(tag instanceof NBTTagList){
            List<NBTBase> list = null;
            try {
                Field field = tag.getClass().getDeclaredField("list");
                field.setAccessible(true);
                list = (List<NBTBase>)field.get(tag);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(list == null) return null;
            List<Object> toReturn = Lists.newArrayList();
            for(NBTBase base : list){
                toReturn.add(getObject(base));
            }
            return toReturn;
        }else if(tag instanceof NBTTagCompound){
            return tag;
        }else if(tag instanceof NBTTagIntArray){
            return ((NBTTagIntArray) tag).c();
        }
        return null;
    }
}