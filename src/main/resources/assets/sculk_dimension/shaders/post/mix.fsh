#version 150

uniform sampler2D FirstSampler;
uniform sampler2D SecondSampler;
in vec2 texCoord;


out vec4 fragColor;
void main(){
    vec4 fc = texture(FirstSampler,texCoord);
    vec4 sc = texture(SecondSampler, texCoord);
    fragColor = mix(fc,sc,0.0);//depricated

}
