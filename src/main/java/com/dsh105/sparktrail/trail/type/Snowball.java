package com.dsh105.sparktrail.trail.type;

import com.dsh105.sparktrail.trail.EffectHolder;
import com.dsh105.sparktrail.trail.PacketEffect;
import com.dsh105.sparktrail.trail.ParticleType;


public class Snowball extends PacketEffect {

    public Snowball(EffectHolder effectHolder) {
        super(effectHolder, ParticleType.SNOWBALL);
    }

    @Override
    public String getNmsName() {
        return "snowballpoof";
    }

    @Override
    public float getSpeed() {
        return 1F;
    }

    @Override
    public int getParticleAmount() {
        return 20;
    }
}