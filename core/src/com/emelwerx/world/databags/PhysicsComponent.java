package com.emelwerx.world.databags;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.physics.bullet.linearmath.btMotionState;

public class PhysicsComponent extends Component{

    private btMotionState motionState;
    private btCollisionObject body;
    private btRigidBody.btRigidBodyConstructionInfo bodyInfo;

    public btMotionState getMotionState() {
        return motionState;
    }

    public void setMotionState(btMotionState motionState) {
        this.motionState = motionState;
    }

    public btCollisionObject getBody() {
        return body;
    }

    public void setBody(btCollisionObject body) {
        this.body = body;
    }

    public btRigidBody.btRigidBodyConstructionInfo getBodyInfo() {
        return bodyInfo;
    }

    public void setBodyInfo(btRigidBody.btRigidBodyConstructionInfo bodyInfo) {
        this.bodyInfo = bodyInfo;
    }
}
